import './App.css';
import SignUp from './components/SignUp/SignUp';
import Home from './components/Home';
import Login from './components/Login/Login';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import { ProtectedRoute } from "./services/ProtectedRoute";
import { Container, Row, Col } from 'react-bootstrap';
import StudentList from './components/StudentList';
import InternshipOfferList from './components/InternshipOfferList';
import InternshipOfferForm from './components/InternshipOfferForm'
import SideBar from "../src/components/Navbar/SideBar";
import './styles/SideBarStyles.css'

function App () {

  return (
    <Router>
      <div className="App">  
        <Container fluid>
          <Row>
              <Col xs={2} id="sidebar-wrapper">      
                <SideBar />
              </Col>
              <Col  xs={10} id="page-content-wrapper">
                <Switch> 
                  <Route path="/" exact component={Login}/>
                  <Route path="/signUp" exact component={SignUp}/>
                  <ProtectedRoute path="/home/:username" exact component={Home}/>
                  <ProtectedRoute path="/formInternshipOffer" exact component={InternshipOfferForm}/>
                  <ProtectedRoute path="/listStudents" exact component={StudentList}/>
                  <ProtectedRoute path="/listInternshipOffer" exact component={InternshipOfferList}/>
                  <Route path="*" exact component={Login}/>
                </Switch> 
              </Col> 
          </Row>

        </Container>           
        
        
      </div>
    </Router>
  );
}

export default App;
