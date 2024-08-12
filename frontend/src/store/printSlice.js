import { createSlice } from '@reduxjs/toolkit';

const printSlice = createSlice({
  name: 'print',
  initialState: {
    userName: '',
    department: '',
    classroom: '',
    files: [],
    copies: 1,
    printType: 'color',
  },
  reducers: {
    setUserDetails: (state, action) => {
      const { userName, department, classroom } = action.payload;
      state.userName = userName;
      state.department = department;
      state.classroom = classroom;
    },
    setFiles: (state, action) => {
      state.files = action.payload;
    },
    setPrintOptions: (state, action) => {
      const { copies, printType } = action.payload;
      state.copies = copies;
      state.printType = printType;
    },
  },
});

export const { setUserDetails, setFiles, setPrintOptions } = printSlice.actions;
export default printSlice.reducer;