import React from 'react';
import { createRoot } from 'react-dom/client';
import { PacientesPage } from './pages/PacientesPage';

const root = createRoot(document.getElementById('root')!);
root.render(
  <React.StrictMode>
    <PacientesPage />
  </React.StrictMode>
);
