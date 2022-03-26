import './App.css';
import { useState, useEffect } from 'react';


function Interest(props) {
  return <div className="interest">{props.value}</div>
}


function User(props) {
  const interests = props.user.interests?.map((interest) => <Interest key={interest} value={interest}/>);

  return (
    <div className="user">
      <div className="username">{props.user.username}</div>
      <div className="content">
        <div className="followers-container">
          <div className="followers">{props.user.followers}</div>
          <div className="following">{props.user.following}</div>
        </div>
        <p className="status">{props.user.status}</p>
        <div className="interests-container">{interests}</div>
        <p className="email">{props.user.email}</p>
        <p className="address">{props.user.address}</p>
      </div>
    </div>
  )
}


function App() {
  const [user, setUser] = useState("User");

  useEffect(() => {
    fetch("http://localhost:8080/api/v1/users/haroldwatkins")
    .then((response) => response.json())
    .then((res) =>
      setUser(res)
    );
  }, [])

  return (
    <div className="App">
      <User user={user}/>
    </div>
  );
}

export default App;
