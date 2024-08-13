/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors:{
        "dark": "#5D576B",
        "primary": "#23CE6B",
        "flash": "#EAEBED",
      },
      fontFamily:{
        "poppins": "'Poppins', sans-serif"
      },
      keyframes:{
        fadeLoop: {
          '0%, 100%': { opacity: '1' },
          '50%': { opacity: '0' },
        },
        fade: {
          '0%': { opacity: '0' },
          '100%': { opacity: '1' },
        },
      },
      animation:{
        fadeLoop: 'fadeLoop 2s ease infinite',
        fade: 'fade .5s ease-in',
      }
    },
  },
  plugins: [],
}