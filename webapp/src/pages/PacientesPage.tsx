import React, { useEffect, useMemo, useState } from 'react';

type Paciente = {
  idPaciente: number;
  dsNome: string;
  dsEmail?: string;
  dsEndereco: string;
  dsBairro: string;
  nrCep: string;
};

const API_BASE = import.meta.env.VITE_API_BASE ?? 'http://localhost:8080';

export function PacientesPage() {
  const [query, setQuery] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [data, setData] = useState<Paciente[]>([]);

  const endpoint = useMemo(() => {
    const q = query.trim();
    const url = new URL(`${API_BASE}/api/pacientes`);
    if (q) url.searchParams.set('q', q);
    return url.toString();
  }, [query]);

  useEffect(() => {
    let cancelled = false;
    setLoading(true);
    setError(null);
    fetch(endpoint)
      .then(async (r) => {
        if (!r.ok) throw new Error(`HTTP ${r.status}`);
        return (await r.json()) as Paciente[];
      })
      .then((rows) => {
        if (!cancelled) setData(rows);
      })
      .catch((e: any) => !cancelled && setError(String(e?.message || e)))
      .finally(() => !cancelled && setLoading(false));
    return () => {
      cancelled = true;
    };
  }, [endpoint]);

  return (
    <div style={{ padding: 24, fontFamily: 'system-ui, sans-serif' }}>
      <h1>Pacientes</h1>
      <div style={{ marginBottom: 16 }}>
        <input
          placeholder="Pesquisar por nome..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          style={{ padding: 8, width: 320 }}
        />
      </div>

      {loading && <p>Carregando...</p>}
      {error && (
        <p style={{ color: 'crimson' }}>
          Erro ao carregar: {error}
        </p>
      )}

      {!loading && !error && (
        <table style={{ borderCollapse: 'collapse', width: '100%' }}>
          <thead>
            <tr>
              <th style={{ textAlign: 'left', borderBottom: '1px solid #ddd', padding: '8px' }}>ID</th>
              <th style={{ textAlign: 'left', borderBottom: '1px solid #ddd', padding: '8px' }}>Nome</th>
              <th style={{ textAlign: 'left', borderBottom: '1px solid #ddd', padding: '8px' }}>Email</th>
              <th style={{ textAlign: 'left', borderBottom: '1px solid #ddd', padding: '8px' }}>CEP</th>
            </tr>
          </thead>
          <tbody>
            {data.map((p) => (
              <tr key={p.idPaciente}>
                <td style={{ borderBottom: '1px solid #eee', padding: '8px' }}>{p.idPaciente}</td>
                <td style={{ borderBottom: '1px solid #eee', padding: '8px' }}>{p.dsNome}</td>
                <td style={{ borderBottom: '1px solid #eee', padding: '8px' }}>{p.dsEmail ?? '-'}</td>
                <td style={{ borderBottom: '1px solid #eee', padding: '8px' }}>{p.nrCep}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
