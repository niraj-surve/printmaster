import React from 'react';
import Home from './pages/Home';
import Print from './pages/Print/Print';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import PaymentSuccess from './pages/PaymentSuccess';

const router = createBrowserRouter([
  {
    path: "/printmaster",
    element: <Home />
  },
  {
    path: "/printmaster/print",
    element: <Print />
  },
  {
    path: "/printmaster/payment/success",
    element: <PaymentSuccess />
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
