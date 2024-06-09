import React, { useState } from 'react';

export const TodoForm = ({ addTodo }) => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [type, setType] = useState('');
  const [dueDate, setDueDate] = useState('');
  const [dueDays, setDueDays] = useState('');
  const [priority, setPriority] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (title) {
      addTodo({ title, description, type, dueDate, dueDays, priority });
      setTitle('');
      setDescription('');
      setType('');
      setDueDate('');
      setDueDays('');
      setPriority('');
    }
  };

  return (
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
      <select
        value={type}
        onChange={(e) => setType(e.target.value)}
        className="todo-input"
      >
        <option value="">Selecione o Tipo</option>
        <option value="Free">Livre</option>
        <option value="Date">Data</option>
        <option value="Period">Período</option>
      </select>
      <input
        type="date"
        value={dueDate}
        onChange={(e) => setDueDate(e.target.value)}
        className="todo-input"
        placeholder="Data de Vencimento"
      />
      <input
        type="number"
        value={dueDays}
        onChange={(e) => setDueDays(e.target.value)}
        className="todo-input"
        placeholder="Dias para Vencimento"
      />
      <input
        type="text"
        value={priority}
        onChange={(e) => setPriority(e.target.value)}
        className="todo-input"
        placeholder="Prioridade da Tarefa"
      />
      <button type="submit" className="todo-btn">Adicionar Tarefa</button>
    </form>
  );
};