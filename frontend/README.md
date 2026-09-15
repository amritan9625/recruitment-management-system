# Recruitment Management System — Frontend

This folder contains the React + Vite frontend for the Recruitment Management System.

## Technology

* React
* Vite
* React Router
* JavaScript
* CSS

## Run Locally

### Prerequisites

* Node.js and npm
* The Recruitment Management System backend running locally

### Setup

From this folder, install dependencies:

```bash
npm install
```

Create a `.env` file in the `frontend` folder:

```env
VITE_API_BASE_URL=http://localhost:8080
```

Start the development server:

```bash
npm run dev
```

Open the local URL shown in the terminal, usually `http://localhost:5173`.

## Available Scripts

| Command           | Purpose                              |
| ----------------- | ------------------------------------ |
| `npm run dev`     | Start the development server         |
| `npm run build`   | Create the production build          |
| `npm run preview` | Preview the production build locally |
| `npm run lint`    | Run ESLint                           |

## Production

The frontend is deployed on Render:

https://recruitment-management-system-frontend-ztke.onrender.com

The production API base URL is configured through the `VITE_API_BASE_URL` environment variable in the hosting service.

For the complete project overview, backend setup, roles, and workflows, see the [root README](../README.md).
