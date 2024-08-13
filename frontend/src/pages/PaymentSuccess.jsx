import React, { useEffect } from 'react'
import { FaCheckCircle } from 'react-icons/fa'
import { useNavigate } from 'react-router-dom';

const PaymentSuccess = () => {
    const navigate = useNavigate();
    useEffect(() => {
        setTimeout(() => {
            navigate('/printmaster/');
        }, 3000);
    }, []);

  return (
    <div className='h-screen w-screen flex flex-col gap-8 justify-center items-center'>
        <FaCheckCircle className='text-8xl text-green-500 animate-fade' />
        <p>Payment successful! Your document is in queue.</p>
    </div>
  )
}

export default PaymentSuccess