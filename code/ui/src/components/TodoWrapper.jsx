import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { TodoList } from './TodoList';
import api from '../service/api';

export const TodoWrapper = () => {
  const [todos, setTodos] = useState([]);

  useEffect(() => {
    api.get('/task')
      .then(response => setTodos(response.data))
      .catch(error => console.error('Error fetching tasks:', error));
  }, []);

  const deleteTodo = (id) => {
    api.delete(`/task/${id}`)
      .then(() => setTodos(todos.filter(todo => todo.id !== id)))
      .catch(error => console.error('Error deleting task:', error));
  };

  const completeTodo = (id) => {
    api.patch(`/task/${id}/done`)
      .then(() => {
        setTodos(todos.map(todo =>
          todo.id === id ? { ...todo, status: 'Completed' } : todo
        ));
      })
      .catch(error => console.error('Error completing task:', error));
  };

  return (
    <div className="TodoWrapper">
      <h1>Lista de Tarefas</h1>
      <Link to="/create" className="create-task-button">Create Task</Link>
      <h2>Em progresso</h2>
      <div className="task-list" style={{
        marginTop: '20px'
      }}>
        {todos.filter(todo => todo.status !== 'Completed').length === 0 && <p>Nenhuma tarefa em progresso</p>}
        {todos.filter(todo => todo.status !== 'Completed').map((item) => (
          <TodoList
            key={item.id}
            task={item}
            deleteTodo={deleteTodo}
            completeTodo={completeTodo}
          />
        ))}
      </div>
      <h2>Finalizadas</h2>
      <div className="task-list">
        {todos.filter(todo => todo.status === 'Completed').length === 0 && <p>Nenhuma tarefa finalizada</p>}
        {todos.filter(todo => todo.status === 'Completed').map((item) => (
          <TodoList
            key={item.id}
            task={item}
            deleteTodo={deleteTodo}
          />
        ))}
      </div>
    </div>
  );
};