import {
    Navigate,
    Route,
    Routes,
} from "react-router-dom"

import LoginPage from "./pages/LoginPage"
import RegisterPage from "./pages/RegisterPage"
import DashboardPage from "./pages/DashboardPage"
import ProtectedRoute from "./routes/ProtectedRoute"
import PublicRoute from "./routes/PublicRoute"
import MySkillsPage from "./pages/MySkillsPage"
import RecommendationsPage from "./pages/RecommendationsPage"
import RoadmapsPage from "./pages/RoadmapsPage"
import AdminPage from "./pages/AdminPage"
import AdminRoute from "./routes/AdminRoute"
import CareerComparisonPage from "./pages/CareerComparisonPage"

function App() {
    return (
        <>

            <Routes>
                <Route
                    path="/"
                    element={<Navigate to="/login" replace />}
                />

                <Route
                    path="/login"
                    element={
                        <PublicRoute>
                            <LoginPage />
                        </PublicRoute>
                    }
                />

                <Route
                    path="/register"
                    element={
                        <PublicRoute>
                            <RegisterPage />
                        </PublicRoute>
                    }
                />

                <Route
                    path="/dashboard"
                    element={
                        <ProtectedRoute>
                            <DashboardPage />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/skills"
                    element={
                        <ProtectedRoute>
                            <MySkillsPage />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/recommendations"
                    element={
                        <ProtectedRoute>
                            <RecommendationsPage />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/career-comparison"
                    element={
                        <ProtectedRoute>
                            <CareerComparisonPage />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/roadmaps"
                    element={
                        <ProtectedRoute>
                            <RoadmapsPage />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/admin"
                    element={
                        <ProtectedRoute>
                            <AdminRoute>
                                <AdminPage />
                            </AdminRoute>
                        </ProtectedRoute>
                    }
                />

            </Routes>


        </>
    )
}

export default App