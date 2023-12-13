console.log("running V2.2");

const ce = React.createElement

class LoginComponent extends React.Component{

    constructor(props){
        super(props);
        this.state = {loginName:"", loginPass:"", createName:"", createPass:""};
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
                ce('span', { id: 'login-mess' }),
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
                ce('span', { id: 'create-mess' })
            )
        );
    }

    changeHandler(e){
        console.log(e.target['id']);
    }



}


ReactDOM.render(
    ce(
        LoginComponent, null, null
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