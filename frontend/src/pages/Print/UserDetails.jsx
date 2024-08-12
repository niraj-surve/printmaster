const UserDetails = ({
  userName,
  setUserName,
  department,
  setDepartment,
  classroom,
  setClassroom,
  nextStep,
}) => {
  // Check if the user details are filled
  const isFormFilled = userName.trim() !== "" && department !== "" && classroom !== "";

  return (
    <div className="h-full flex flex-col justify-between">
      <h2 className="text-xl font-bold mb-4 relative top-0">User Details</h2>
      <div className="mb-4">
        <label className="block text-sm font-medium text-gray-700 mb-2">
          Name
        </label>
        <input
          type="text"
          className="w-full p-2 border rounded"
          value={userName}
          onChange={(e) => setUserName(e.target.value)}
          required
        />
      </div>
      <div className="mb-4">
        <label className="block text-sm font-medium text-gray-700 mb-2">
          Department
        </label>
        <select
          name="department"
          className="w-full p-2 border rounded"
          value={department}
          id="department"
          onChange={(e) => setDepartment(e.target.value)}
          required
        >
          <option value="computer">Computer Engineering</option>
          <option value="mechanical">Mechanical Engineering</option>
          <option value="civil">Civil Engineering</option>
          <option value="electronics">E&TC Engineering</option>
        </select>
      </div>
      <div className="mb-4">
        <label className="block text-sm font-medium text-gray-700 mb-2">
          Classroom
        </label>
        <select
          name="classroom"
          className="w-full p-2 border rounded"
          value={classroom}
          id="classroom"
          onChange={(e) => setClassroom(e.target.value)}
          required
        >
          <option value="FE">First Year</option>
          <option value="SE">Second Year</option>
          <option value="TE">Third Year</option>
          <option value="BE">Final Year</option>
        </select>
      </div>
      <button
        type="button"
        onClick={nextStep}
        disabled={!isFormFilled}
        className={`w-full p-2 text-white rounded ${
          isFormFilled ? "bg-primary hover:bg-primary" : "bg-gray-400 cursor-not-allowed"
        }`}
      >
        Next
      </button>
    </div>
  );
};

export default UserDetails;