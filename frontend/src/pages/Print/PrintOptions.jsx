import React, { useState } from "react";

const PrintOptions = ({
  copies,
  setCopies,
  printType,
  setPrintType,
  paperSize,
  setPaperSize,
  prevStep,
}) => {
  // Ensure copies is a valid positive number
  const isCopiesValid = copies > 0;

  return (
    <div className="h-full flex flex-col justify-between">
      <h2 className="text-xl font-bold mb-4">Print Options</h2>
      <div className="mb-4">
        <label className="block text-sm font-medium text-gray-700 mb-2">
          Number of Copies
        </label>
        <input
          type="number"
          className="w-full p-2 border rounded"
          value={copies}
          min="1"
          onChange={(e) => setCopies(parseInt(e.target.value, 10))}
          required
        />
      </div>
      <div className="mb-4">
        <label className="block text-sm font-medium text-gray-700 mb-2">
          Print Type
        </label>
        <select
          className="w-full p-2 border rounded"
          value={printType}
          onChange={(e) => setPrintType(e.target.value)}
          required
        >
          <option value="color">Color</option>
          <option value="bw">Black & White</option>
        </select>
      </div>
      <div className="mb-4">
        <label className="block text-sm font-medium text-gray-700 mb-2">
          Paper Size
        </label>
        <select
          className="w-full p-2 border rounded"
          value={paperSize}
          onChange={(e) => setPaperSize(e.target.value)}
          required
        >
          <option value="A4">A4</option>
          <option value="A3">A3</option>
          <option value="Letter">Letter</option>
          <option value="Legal">Legal</option>
        </select>
      </div>
      <div className="flex justify-between gap-8">
        <button
          type="button"
          onClick={prevStep}
          className="w-full p-2 bg-gray-300 text-black rounded hover:bg-gray-400"
        >
          Back
        </button>
        <button
          type="submit"
          disabled={!isCopiesValid}
          className={`w-full p-2 text-white rounded ${
            isCopiesValid
              ? "bg-green-500 hover:bg-green-600"
              : "bg-gray-400 cursor-not-allowed"
          }`}
        >
          Submit
        </button>
      </div>
    </div>
  );
};

export default PrintOptions;