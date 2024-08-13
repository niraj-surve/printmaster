import React, { useState } from "react";
import UserDetails from "./UserDetails";
import FileUpload from "./FileUpload";
import PrintOptions from "./PrintOptions";
import { useDispatch, useSelector } from "react-redux";
import { setUserDetails, setFiles, setPrintOptions } from "../../store/printSlice";
import { Link, redirect, useNavigate } from 'react-router-dom';
import axios from 'axios';

const Print = () => {
  const [currentStep, setCurrentStep] = useState(1);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  // State for each step
  const [userName, setUserName] = useState("");
  const [department, setDepartment] = useState("computer");
  const [classroom, setClassroom] = useState("FE");
  const [files, setFilesState] = useState([]); // Local state for file objects
  const [copies, setCopies] = useState(1);
  const [printType, setPrintType] = useState("color");
  const [paperSize, setPaperSize] = useState("A4");

  // Function to move to the next step
  const nextStep = () => {
    setCurrentStep((prevStep) => prevStep + 1);
  };

  // Function to move to the previous step
  const prevStep = () => {
    setCurrentStep((prevStep) => prevStep - 1);
  };

  // Function to handle form submission
  const handleSubmit = async (event) => {
    event.preventDefault();
  
    // Create FormData object
    const formData = new FormData();
    formData.append("userName", userName);
    formData.append("department", department);
    formData.append("classroom", classroom);
    formData.append("copies", copies);
    formData.append("printType", printType);
    formData.append("paperSize", paperSize);
  
    // Append each file to FormData
    files.forEach((file) => {
      formData.append("file", file);
    });
  
    try {
      const response = await axios.post("http://localhost:8080/api/print/pay", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      
      window.location.href = response.data.paymentUrl;
      // Handle response or redirect
    } catch (error) {
      console.error("Error initiating payment:", error);
    }
  };
  

  return (
    <div className="bg-dark h-screen flex flex-col items-center py-8 gap-10">
      <div className="flex flex-col items-center gap-4">
        <Link to={'/printmaster'}>
          <h1 className="font-black text-flash text-4xl cursor-pointer">
            Print<span className="text-primary animate-fadeLoop">Master</span>
          </h1>
        </Link>
        <p className="text-sm text-slate-400">Please enter your printing details...</p>
      </div>
      <div className="w-2/5 h-3/4 mx-auto p-4 bg-white shadow-md rounded">
        <form className="h-full" onSubmit={handleSubmit}>
          {currentStep === 1 && (
            <UserDetails
              userName={userName}
              setUserName={setUserName}
              department={department}
              setDepartment={setDepartment}
              classroom={classroom}
              setClassroom={setClassroom}
              nextStep={nextStep}
            />
          )}
          {currentStep === 2 && (
            <FileUpload
              files={files}
              setFiles={setFilesState}
              nextStep={nextStep}
              prevStep={prevStep}
            />
          )}
          {currentStep === 3 && (
            <PrintOptions
              copies={copies}
              setCopies={setCopies}
              printType={printType}
              setPrintType={setPrintType}
              setPaperSize={setPaperSize}
              prevStep={prevStep}
            />
          )}
        </form>
      </div>
    </div>
  );
};

export default Print;