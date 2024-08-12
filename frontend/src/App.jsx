import React from 'react';
import Home from './pages/Home';
import Print from './pages/Print/Print';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

const router = createBrowserRouter([
  {
    path: "/printmaster",
    element: <Home />
  },
  {
    path: "/printmaster/print",
    element: <Print />
  },
]);

function App() {
  return (
    <>
      <RouterProvider router={router} />
    </>
  )
}

export default App
