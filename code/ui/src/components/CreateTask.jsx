import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import api from '../service/api';
import FeedBackAlert from './FeedBackAlert'

export const CreateTask = () => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [type, setType] = useState('Free');
  const [dueDate, setDueDate] = useState('');
  const [dueDays, setDueDays] = useState('');
  const [priority, setPriority] = useState('Low');
  const [isLoading, setIsLoading] = useState(false);
  const [erroMessage, setErroMessage] = useState(null);
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    setIsLoading(true);
    if(invalidFields()) return
    e.preventDefault();
    const newTask = { title, description, type, dueDate, dueDays, priority };
    api.post('/task', newTask)
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
      .catch(error => setErroMessage(error.response.data.title || error.response.data.errorMessage))
      .finally(() => setIsLoading(false)) 
  };

  const invalidFields = () => {
    if (!title) {
      setErroMessage('O título é obrigatório')
      return true
    }
    if (!description) {
      setErroMessage('A descrição é obrigatória')
      return true
    }
    if (!priority) {
      setErroMessage('A prioridade é obrigatória')
      return true
    }
    if (!type) {
      setErroMessage('O tipo é obrigatório')
      return true
    }
    if (type === "Date" && !dueDate) {
      setErroMessage('A data de vencimento é obrigatória')
      return true
    }
    if (type === "Period" && !dueDays) {
      setErroMessage('Os dias para vencimento são obrigatórios')
      return true
    }
    return false
  }

  return (
    <>
      {showSuccessMessage && <FeedBackAlert message='Tarefa criada com sucesso' type='success' />}
      <div className="CreateTask">
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
            >
              <option value="">Selecione o Tipo</option>
              <option value="Free">Livre</option>
              <option value="Date">Data</option>
              <option value="Period">Prazo</option>
            </select>
          </div>
          {type === "Date" && (
            <input
              type="date"
              value={dueDate}
              onChange={(e) => setDueDate(e.target.value)}
              className="todo-input"
              placeholder="Data de Vencimento"
              min={new Date().toISOString().split('T')[0]}
            />
          )}
          {type === "Period" && (
            <input
              type="number"
              value={dueDays}
              onChange={(e) => setDueDays(e.target.value)}
              className="todo-input"
              placeholder="Dias para Vencimento"
            />
          )}
          {erroMessage && <p className='error-message'>{erroMessage}</p>}
          <button type="submit" className="todo-btn" disabled={isLoading}>
            {isLoading ? 'Criando...' : 'Criar'}
          </button>
        </form>
      </div>
    </>
  );
};