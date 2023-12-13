console.log("running V2.2");

const ce = React.createElement

const csrfToken = document.getElementById("csrfToken").value;
const validateRoute = document.getElementById("validateRoute").value;
const messagesRoute = document.getElementById("messagesRoute").value;
const createRoute = document.getElementById("createRoute").value;
const putRoute = document.getElementById("putRoute").value;
const logOutRoute = document.getElementById("logOutRoute").value;

class Main4Component extends React.Component{
    constructor(props){
        super(props);
        this.state = {loggedIn:false};
    }

    render(){
        if(this.state.loggedIn)
            return ce(MsgBoardComponent, {doLogout: () => this.setState({loggedIn:false})});
        return ce(LoginComponent, { doLogin: () => this.setState({loggedIn:true}) });
    }
}

class LoginComponent extends React.Component{

    constructor(props){
        super(props);
        this.state = {
        loginName:"", 
        loginPass:"", 
        createName:"", 
        createPass:"",
        loginMess:"",
        createMess:""
        };
    }

    render(){
        return ce('div', { id: 'login-section' },
            ce('div', null,
                'Username: ',
                ce('input', { type: 'text', id: 'loginName', value: this.state.loginName, onChange: e=> this.changeHandler(e)}),
                ce('br'),
                'Password: ',
                ce('input', { type: 'password', id: 'loginPass', value: this.state.loginPass, onChange: e=> this.changeHandler(e) }),
                ce('br'),
                ce('button', { onClick: e => this.login(e) }, 'Login'),
                ce('span', { id: 'login-mess'}, this.state.loginMess),
                ce('br')
            ),
            ce('div', null,
                'Username: ',
                ce('input', { type: 'text', id: 'createName', value: this.state.createName, onChange: e=> this.changeHandler(e) }),
                ce('br'),
                'Password: ',
                ce('input', { type: 'password', id: 'createPass', value: this.state.createPass, onChange: e=> this.changeHandler(e) }),
                ce('br'),
                ce('button', { onClick: e => this.createUser(e) }, 'Create User'),
                ce('span', { id: 'create-mess'}, this.state.createMess),
                ce('br')
            )
        );
    }

    changeHandler(e){
        this.setState({[e.target['id']]: e.target.value });
    }

    login(e){
        const username = this.state.loginName;
        const password = this.state.loginPass;
        fetch(validateRoute, { 
            method: 'POST',
            headers: {'Content-Type': 'application/json', 'Csrf-Token':csrfToken},
            body: JSON.stringify({ username, password })
        }).then(res => res.json()).then(data => {
            console.log(data);
            if (data)
                this.props.doLogin();
            else
                this.setState({loginMess: "Login Failed"});
        });
    }

}

class MsgBoardComponent extends React.Component{

    constructor(props){
        super(props);
        this.state = {messages: []}
    }

    render(){
        return ce('div', null, 'Message App V2.2',
                    ce('br'),
                    ce('ul', null,
                        //fetch: 
                        ce('li', null, '1900-01-01 18:00:00 A TO B: Hi, B!') 
                    ),
                    ce('br'),
                    ce('div', null, 
                        ce('input', {type:"text"}),
                        ce('button', null, 'Put Message')
                    ),
                    ce('br'),
                    ce('button', {onClick: e => this.props.doLogout()}, 'Log Out')
                );
    }
}

ReactDOM.render(
    ce(
        Main4Component, null, null
    ),
    document.getElementById('react-root')
);





















/*
function StatelessHello(props) {
    return ce('div', null, `Hello ${props.toWhat}`);    
}

class Hello extends React.Component {

    constructor(props){
        super(props);
        this.state = {clickCount : 0};
    }


    render() {
      return ce('div', {onClick: (e) => this.clickHandler(e)}, `Hello ${this.props.toWhat} - click count ${this.state.clickCount}`);
    }

    clickHandler(e){
        this.setState({clickCount: this.state.clickCount + 1});
    }
}

class SimpleForm extends React.Component {
    constructor(props){
        super(props);
        this.state = {textInput:""}
    }
    render(){
        return ce('input', {type:"text", value:this.state.textInput, onChange: (e) => this.changeHandler(e)});
    }

    changeHandler(e){
        this.setState({textInput: event.target.value});
    }
}

ReactDOM.render(
    ce(
        'div', null, 
        ce(Hello, {toWhat:'World'}, null), 
        ce(StatelessHello, {toWhat: 'Earth'}, null),
        ce(SimpleForm, null, null)
    ),
    document.getElementById('react-root')
    

);
*/