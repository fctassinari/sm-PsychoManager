import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { Box } from '@mui/material';
import Layout from './components/Layout/Layout';
import Home from './pages/Home/Home';
import Pacientes from './pages/Pacientes/Pacientes';
import PacienteForm from './pages/Pacientes/PacienteForm';
import Produtos from './pages/Produtos/Produtos';
import ProdutoForm from './pages/Produtos/ProdutoForm';
import Agenda from './pages/Agenda/Agenda';
import Relatorios from './pages/Relatorios/Relatorios';

function App() {
  return (
    <Box sx={{ display: 'flex', minHeight: '100vh' }}>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/pacientes" element={<Pacientes />} />
          <Route path="/pacientes/novo" element={<PacienteForm />} />
          <Route path="/pacientes/editar/:id" element={<PacienteForm />} />
          <Route path="/pacientes/visualizar/:id" element={<PacienteForm />} />
          <Route path="/produtos" element={<Produtos />} />
          <Route path="/produtos/novo" element={<ProdutoForm />} />
          <Route path="/produtos/editar/:id" element={<ProdutoForm />} />
          <Route path="/agenda" element={<Agenda />} />
          <Route path="/relatorios" element={<Relatorios />} />
        </Routes>
      </Layout>
    </Box>
  );
}

export default App;
