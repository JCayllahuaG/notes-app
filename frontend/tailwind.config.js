/** @type {import('tailwindcss').Config} */
import animations from '@midudev/tailwind-animations';
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx,vue}'],
  theme: {
    extend: {
      dropShadow: {
        glow: ['0 0px 20px rgba(255,255, 255, 0.35)', '0 0px 65px rgba(255, 255,255, 0.2)']
      }
    }
  },
  plugins: [animations]
};
