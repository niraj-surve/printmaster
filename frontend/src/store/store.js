import { configureStore } from '@reduxjs/toolkit';
import printReducer from '../store/printSlice.js';

const store = configureStore({
  reducer: {
    print: printReducer,
  },
});

export default store;
