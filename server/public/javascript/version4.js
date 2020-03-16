console.log("Running version 4.");

const ce = React.createElement
const csrfToken = document.getElementById("csrfToken").value;
const validateRoute = document.getElementById("validateRoute").value;
const tasksRoute = document.getElementById("tasksRoute").value;
const createRoute = document.getElementById("createRoute").value;
const deleteRoute = document.getElementById("deleteRoute").value;
const addRoute = document.getElementById("addRoute").value;
const logoutRoute = document.getElementById("logoutRoute").value;

class Version4MainComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = { loggedIn: false };
  }

  render() {
    if (this.state.loggedIn) {
      return ce(TaskListComponent, { doLogout: () => this.setState( { loggedIn: false})});
    } else {
      return ce(LoginComponent, { doLogin: () => this.setState( { loggedIn: true }) });
    }
  }
}

class LoginComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      loginName: "", 
      loginPass: "", 
      createName: "", 
      createPass: "",
      loginMessage: "",
      createMessage: ""
    };
  }

  render() {
    return ce('div', null,
      ce('h2', null, 'Login:'),
      ce('br'),
      'Username: ',
      ce('input', {type: "text", id: "loginName", value: this.state.loginName, onChange: e => this.changerHandler(e)}),
      ce('br'),
      'Password: ',
      ce('input', {type: "password", id: "loginPass", value: this.state.loginPass, onChange: e => this.changerHandler(e)}),
      ce('br'),
      ce('button', {onClick: e => this.login(e)}, 'Login'),
      ce('span', {id: "login-message"}, this.state.loginMessage),
      ce('h2', null, 'Create User:'),
      ce('br'),
      'Username: ',
      ce('input', {type: "text", id: "createName", value: this.state.createName, onChange: e => this.changerHandler(e)}),
      ce('br'),
      'Password: ',
      ce('input', {type: "password", id: "createPass", value: this.state.createPass, onChange: e => this.changerHandler(e)}),
      ce('br'),
      ce('button', {onClick: e => this.createUser(e)}, 'Create User'),
      ce('span', {id: "create-message"}, this.state.createMessage)
    );
  }

  changerHandler(e) {
    this.setState({ [e.target['id']]: e.target.value });
  }

  login(e) {
    const username = this.state.loginName;
    const password = this.state.loginPass;
    fetch(validateRoute, { 
      method: 'POST',
      headers: {'Content-Type': 'application/json', 'Csrf-Token': csrfToken },
      body: JSON.stringify({ username, password })
    }).then(res => res.json()).then(data => {
      if(data) {
        this.props.doLogin();
      } else {
        this.setState({ loginMessage: "Login Failed" });
      }
    });
  }

  createUser() {
    const username = this.state.createName;
    const password = this.state.createPass;
    fetch(createRoute, { 
      method: 'POST',
      headers: {'Content-Type': 'application/json', 'Csrf-Token': csrfToken },
      body: JSON.stringify({ username, password })
    }).then(res => res.json()).then(data => {
      if(data) {
        this.props.doLogin();
      } else {
        this.setState({ createMessage: "User Creation Failed"});
      }
    });
  }
}

class TaskListComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = { tasks: [], newTask: "", taskMessage: "" };
  }

  componentDidMount() {
    this.loadTasks();
  }

  render() {
    return ce('div', null, 
      'Task List',
      ce('br'),
      ce('ul', null,
        this.state.tasks.map((task, index) => ce('li', { key: index, onClick: e => this.handleDeleteClick(index) }, task))
      ),
      ce('br'),
      ce('div', null,
        ce('input', {type: 'text', value: this.state.newTask, onChange: e => this.handleChange(e) }),
        ce('button', {onClick: e => this.handleAddClick(e)}, 'Add Task'),
        this.state.taskMessage
      ),
      ce('br'),
      ce('button', { onClick: e => this.props.doLogout() }, 'Log out')
    );
  }

  loadTasks() {
    fetch(tasksRoute).then(res => res.json()).then(tasks => this.setState({ tasks }));
  }

  handleChange(e) {
    this.setState({newTask: e.target.value})
  }

  handleAddClick(e) {
    fetch(addRoute, { 
      method: 'POST',
      headers: {'Content-Type': 'application/json', 'Csrf-Token': csrfToken },
      body: JSON.stringify(this.state.newTask)
    }).then(res => res.json()).then(data => {
      if(data) {
        this.loadTasks();
        this.setState({ taskMessage: "", newTask: "" });
      } else {
        this.setState({ taskMessage: "Failed to add." });
      }
    });
  }

  handleDeleteClick(i) {
    fetch(deleteRoute, { 
      method: 'POST',
      headers: {'Content-Type': 'application/json', 'Csrf-Token': csrfToken },
      body: JSON.stringify(i)
    }).then(res => res.json()).then(data => {
      if(data) {
        this.loadTasks();
        this.setState({ taskMessage: "" });
      } else {
        this.setState({ taskMessage: "Failed to delete."});
      }
    });
  }
}

ReactDOM.render(
  ce(Version4MainComponent, null, null),
  document.getElementById('react-root')
);

/*
function StatelessHello(props) {
  return ce('div', null, `Hello ${props.toWhat}`);
}

class Hello extends React.Component {
  constructor(props) {
    super(props);
    this.state = { clickCount: 0 };
  }

  render() {
    return ce('div', {onClick: (e) => this.clickHandler(e)}, `Hello ${this.props.toWhat} - click count ${this.state.clickCount}`);
  }

  clickHandler(e) {
    this.setState({clickCount: this.state.clickCount + 1});
  }
}

class SimpleForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {textInput: ""}
  }

  render() {
    return ce('input', {type: "text", value: this.state.textInput, onChange: (e) => this.changeHandler(e)});
  }

  changeHandler(e) {
    this.setState({textInput: event.target.value});
  }
}

ReactDOM.render(
  ce('div', null, 
    ce(Hello, {toWhat: 'World'}, null), 
    ce(StatelessHello, {toWhat: 'Earth'}, null),
    ce(SimpleForm, null, null)
  ),
  document.getElementById('react-root')
);
*/