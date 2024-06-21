import React from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';

export const TodoList = ({ task, deleteTodo, completeTodo }) => {
  const renderMoreDetails = () => {
    if (task.type === "Date") {
      return renderMinDateType()
    }
    if (task.type === "Period") {
      return renderMinPeriodType()
    }
    return <></>
  }

  const renderMinDateType = () => {
    const formattedDate = new Date(task.dueDate).toLocaleDateString('pt-BR');
    return (
      <>
        <span><strong>Dia de vencimento:</strong> {formattedDate}</span>
        {task.status !== "Completed" &&
          <span><strong>Status:</strong> {task.statusDescription.split('- ')[1]}</span>
        }
      </>
    )
  }

  const renderMinPeriodType = () => {
    return (
      <>
        <span><strong>Prazo:</strong> {task.dueDays} {task.dueDays > 1 ? 'dias' : 'dia'}</span>
        {task.status !== "Completed" &&
          <span><strong>Status:</strong> {task.statusDescription.split('- ')[1]}</span>
        }
      </>
    )
  }

  const statusDescriptionBadget = () => {
    if (task.status === 'Late') {
      return <span className="badget late">{task.statusDescription}</span>
    }
  }

  const renderBadgets = () => {
    let badgets = []
    if (task.priority === "Low") {
      badgets.push(<span key="low" className="badget low">Baixa</span>)
    } else if (task.priority === "Medium") {
      badgets.push(<span key="medium" className="badget medium">Média</span>)
    } else if (task.priority === "High") {
      badgets.push(<span key="high" className="badget high">Alta</span>)
    }

    if (task.type === "Free") {
      badgets.push(<span key="free" className="badget free">Livre</span>)
    } else if (task.type === "Date") {
      badgets.push(<span key="date" className="badget date">Data</span>)
    } else if (task.type === "Period") {
      badgets.push(<span key="period" className="badget period">Prazo</span>)
    }

    badgets.push(statusDescriptionBadget())

    return badgets
  }

  return (
    <div className={`todo-task task-${task.status}`}>
      <div className='task-details'>
        <div style={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'flex-start',
          justifyContent: 'flex-start',
          margin: '10px 0'
        }}>
          <span><strong>Título:</strong> {task.title}</span>
          {task.description && <span><strong>Descrição:</strong> {task.description}</span>}
          {renderMoreDetails()}
        </div>
        <div className="badgets">
          {renderBadgets()}
        </div>
      </div>

      <div className='task-actions'>
        {task.status !== 'Completed' && (
          <>
            <button onClick={() => completeTodo(task.id)} className="complete-btn">Complete</button>
            <Link to={`/edit/${task.id}`} className="edit-btn">Edit</Link>
          </>
        )}
        <FontAwesomeIcon
          className="delete-icon"
          icon={faTrash}
          onClick={() => deleteTodo(task.id)}
        />
      </div>
    </div>
  );
};