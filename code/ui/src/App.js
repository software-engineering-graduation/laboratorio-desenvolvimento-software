import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import { TodoWrapper } from './components/TodoWrapper';
import { CreateTask } from './components/CreateTask';
import { EditTask } from './components/EditTask';
import './App.css';

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path='/' element={<TodoWrapper />} />
          <Route path='/create' element={<CreateTask />} />
          <Route path='/edit/:id' element={<EditTask />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;