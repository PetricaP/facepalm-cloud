import './App.css';
import { useState, useEffect } from 'react';
import Navbar from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container'
import Nav from 'react-bootstrap/Nav';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import React from 'react';


interface UserProps {
  interests: string[],
  username: string,
  followers: number,
  following: number,
  status: string,
  email: string,
  address: string,
  pinnedImage: string
}

interface InterestProps {
  value: string
}


function Interest(props: InterestProps) {
  return <div className="interest">{props.value}</div>
}


function User(props: UserProps) {
  const interests = props.interests.map((interest) => <Interest key={interest} value={interest}/>);

  return (
    <div className="user">
      <div className="username">{props.username}</div>
      <div className="content">
        <div className="followers-container">
          <div className="followers">{props.followers}</div>
          <div className="following">{props.following}</div>
        </div>
        <p className="status">{props.status}</p>
        <div className="interests-container">{interests}</div>
        <p className="email">{props.email}</p>
        <p className="address">{props.address}</p>
      </div>
    </div>
  )
}


export function App() {
  const [userProps, setUser] = useState<UserProps | undefined>(undefined);

  useEffect(() => {
    fetch("http://localhost:8080/api/v1/users/haroldwatkins")
      .then((response) => response.json())
      .then((res) =>
        setUser(res)
      );
  }, []);

  return (
    <div className="App">
      <Row>
        <Col className="profile-area">
          <Row>
            <Col className="photo-area">
              {userProps && <img src={userProps.pinnedImage} width="200"/>}
            </Col>
            <Col className="description-area">
              {userProps && <User {...userProps}/>}
            </Col>
          </Row>
          <Row className="posts-area"/>
        </Col>
        <Col className="sidebar-area"/>
      </Row>
    </div>
  );
}

export default App;
