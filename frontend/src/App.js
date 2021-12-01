import "./App.css";
import SignUp from "./components/SignUp/SignUp";
import Login from "./components/Login/Login";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { ProtectedRoute } from "./services/ProtectedRoute";
import { ROUTES } from "./Utils/ROUTES";
import NavigationBar from "../src/components/Navbar/NavigationBar";

function App() {
  return (
    <Router>
      <div className="App">
        <NavigationBar />
        <Switch>
          <Route path="/" exact component={Login} />
          <Route path="/signUp" exact component={SignUp} />

          {ROUTES.map((route, i) => (
            <ProtectedRoute
              key={i}
              path={route.link}
              exact
              component={route.component}
              accessValid={route.accessValid}
            />
          ))}

          <ProtectedRoute path="*" exact component={Login} />
        </Switch>
      </div>
    </Router>
  );
}

export default App;
