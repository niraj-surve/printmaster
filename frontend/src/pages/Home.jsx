import React from "react";
import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div className="h-screen bg-dark text-white flex justify-center items-center">
      <div className="flex flex-col items-center gap-16">
        <h1 className="font-black text-flash text-8xl hover:scale-[1.1] fade cursor-pointer ">
          Print<span className="text-primary">Master</span>
        </h1>
        <Link to={'/print'}>
          <button className="bg-transparent text-primary text-2xl font-semibold border border-1 border-primary py-4 px-8 rounded-lg hover:bg-primary hover:text-flash fade">Start</button>
        </Link>
      </div>
    </div>
  );
};

export default Home;
