const FileUpload = ({ files, setFiles, nextStep, prevStep }) => {
  const handleFileChange = (event) => {
    const selectedFiles = Array.from(event.target.files); // Convert FileList to Array
    setFiles(selectedFiles);
  };

  return (
    <div className="h-full flex flex-col justify-between">
      <h2 className="text-xl font-bold relative top-0">Upload Files</h2>
      <div className="flex items-center justify-center border border-dashed rounded-lg h-2/3 border-primary p-4">
        <label
          htmlFor="file"
          className="text-sm font-medium text-gray-400 w-full h-full flex justify-center items-center cursor-pointer"
        >
          Select your files to print
        </label>
        <input
          id="file"
          type="file"
          className="w-full hidden"
          onChange={handleFileChange}
          multiple
          required
        />
      </div>
      {/* Display the names of the selected files */}
      <div className="mt-4 text-sm text-gray-600">
        {files.length > 0 ? (
          files.map((file, index) => <p key={index}>{file.name}</p>)
        ) : (
          <p>No files selected</p>
        )}
      </div>
      <div className="flex justify-between gap-8 mt-4">
        <button
          type="button"
          onClick={prevStep}
          className="w-full p-2 bg-gray-300 text-black rounded hover:bg-gray-400"
        >
          Back
        </button>
        <button
          type="button"
          onClick={nextStep}
          disabled={files.length === 0}
          className={`w-full p-2 text-white rounded ${
            files.length > 0 ? "bg-primary hover:bg-primary" : "bg-gray-400 cursor-not-allowed"
          }`}
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default FileUpload;