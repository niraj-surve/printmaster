import React, { useState } from "react";
import UserDetails from "./UserDetails";
import FileUpload from "./FileUpload";
import PrintOptions from "./PrintOptions";
import { useDispatch } from "react-redux";
import {
  setUserDetails,
  setFiles,
  setPrintOptions,
} from "../../store/printSlice";
import { Link } from 'react-router-dom';

const Print = () => {
  const [currentStep, setCurrentStep] = useState(1);
  const dispatch = useDispatch();

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
  const handleSubmit = (event) => {
    event.preventDefault();

    // Extract file metadata
    const fileMetadata = files.map(file => ({
      name: file.name,
      type: file.type,
      size: file.size,
    }));

    // Dispatch actions to update the Redux store with file metadata
    dispatch(
      setUserDetails({
        userName,
        department,
        classroom,
      })
    );
    dispatch(setFiles(fileMetadata)); // Only store metadata
    dispatch(
      setPrintOptions({
        copies,
        printType,
      })
    );
    console.log("Form submitted with:", {
      userName,
      department,
      classroom,
      fileMetadata,
      copies,
      printType,
      paperSize
    });
    // Here you can navigate to the payment page or perform further actions
  };

  return (
    <div className="bg-dark h-screen flex flex-col items-center py-8 gap-10">
      <div className="flex flex-col items-center gap-4">
        <Link to={'/'}>
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