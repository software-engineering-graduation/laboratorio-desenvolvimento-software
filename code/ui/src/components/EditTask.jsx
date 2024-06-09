import React, { useState, useEffect } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';

import api from '../service/api';
import FeedBackAlert from './FeedBackAlert'

export const EditTask = () => {
    const { id } = useParams();
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [type, setType] = useState('');
    const [dueDate, setDueDate] = useState('');
    const [dueDays, setDueDays] = useState('');
    const [priority, setPriority] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [erroMessage, setErroMessage] = useState(null);
    const [showSuccessMessage, setShowSuccessMessage] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        api.get(`/task/${id}`)
            .then(response => {
                console.log(response.data);
                const task = response.data;
                setTitle(task.title);
                setDescription(task.description);
                setType(task.type);
                setDueDate(new Date(task.dueDate).toISOString().split('T')[0]);
                setDueDays(task.dueDays);
                setPriority(task.priority);
            })
            .catch(error => console.error('Error fetching task:', error));
    }, [id]);

    const handleSubmit = (e) => {
        setIsLoading(true);
        e.preventDefault();
        const updatedTask = { title, description, type, dueDate, dueDays, priority };
        api.put(`/task/${id}`, updatedTask)
            .then(() => {
                setShowSuccessMessage(true)
                setIsLoading(false)
                setTimeout(() => {
                    setShowSuccessMessage(false)
                }, 1000)
                setTimeout(() => {
                    navigate('/')
                }, 1500)
            })
            .catch(error => setErroMessage(error.response.data.title))
    };

    return (
        <>
            {showSuccessMessage && <FeedBackAlert message='Tarefa editada com sucesso' type='success' />}
            <div className="EditTask">
                <Link to="/" className="back-button">&lt;</Link>
                <h1>Criar Tarefa</h1>
                <form className="TodoForm" onSubmit={handleSubmit}>
                    <input
                        type="text"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        className="todo-input"
                        placeholder="Título da Tarefa"
                    />
                    <textarea
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        className="todo-input"
                        placeholder="Descrição da Tarefa"
                    />
                    <div className='input-container'>
                        <label htmlFor="priority">Prioridade</label>
                        <select
                            value={priority}
                            onChange={(e) => setPriority(e.target.value)}
                            className="todo-input"
                            name='priority'
                        >
                            <option value="">Selecione a Prioridade</option>
                            <option value="Low">Baixa</option>
                            <option value="Medium">Média</option>
                            <option value="High">Alta</option>
                        </select>
                    </div>
                    <div className='input-container'>
                        <label htmlFor="type">Tipo</label>
                        <select
                            value={type}
                            onChange={(e) => setType(e.target.value)}
                            className="todo-input"
                            name='type'
                            disabled={true}
                        >
                            <option value="">Selecione o Tipo</option>
                            <option value="Free">Livre</option>
                            <option value="Date">Data</option>
                            <option value="Period">Prazo</option>
                        </select>
                    </div>
                    {type === "Date" && (
                        <input
                            disabled={true}
                            type="date"
                            value={dueDate}
                            onChange={(e) => setDueDate(e.target.value)}
                            className="todo-input"
                            placeholder="Data de Vencimento"
                        />
                    )}
                    {type === "Period" && (
                        <input
                            disabled={true}
                            type="number"
                            value={dueDays}
                            onChange={(e) => setDueDays(e.target.value)}
                            className="todo-input"
                            placeholder="Dias para Vencimento"
                        />
                    )}
                    {erroMessage && <p className='error-message'>{erroMessage}</p>}
                    <button type="submit" className="todo-btn" disabled={isLoading}>
                        {isLoading ? 'Editando...' : 'Editar'}
                    </button>
                </form>
            </div>
        </>
    );
};