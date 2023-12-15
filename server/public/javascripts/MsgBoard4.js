
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
        createMess:"",
        putMess:"" //mess = the error message being displayed, not the message in the app.
        };
    }

    render(){
        return ce('div', { id: 'login-section' },
            ce('div', null,
                'Username: ',
                ce('input', { type: 'text', id: 'loginName', value: this.state.loginName, onChange: e => this.changeHandler(e)}),
                ce('br'),
                'Password: ',
                ce('input', { type: 'password', id: 'loginPass', value: this.state.loginPass, onChange: e => this.changeHandler(e) }),
                ce('br'),
                ce('button', { onClick: e => this.login(e) }, 'Login'),
                ce('span', { id: 'login-mess'}, this.state.loginMess),
                ce('br')
            ),
            ce('div', null,
                'Username: ',
                ce('input', { type: 'text', id: 'createName', value: this.state.createName, onChange: e => this.changeHandler(e) }),
                ce('br'),
                'Password: ',
                ce('input', { type: 'password', id: 'createPass', value: this.state.createPass, onChange: e => this.changeHandler(e) }),
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
            this.setState({ createMess: "User Creation Failed"});
          }
        });
    }


}

class MsgBoardComponent extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
            messageList: [],
            messageTo: '',
            messageContent: ''
        };
    }

    componentDidMount() {
        this.loadMessages();
    }

    renderMessageItem(message) {
        const toPart = message.to ? ` TO ${message.to}` : ' TO PUBLIC';
        //const sentAtFormatted = message.sentAt; // Format if necessary
        return ce('li', 
            { key: message.id, onClick: () => this.handlePutClick(message) },
            `${message.sentAt} ${message.from}${toPart}: ${message.content}`
        );
    }

    handleToInputChange = (e) => {
        this.setState({messageTo: e.target.value});
        //this.setState({ [e.target.id]: e.target.value });
    }

    handleContentInputChange = (e) => {
        this.setState({messageContent: e.target.value});
        //this.setState({ [e.target.id]: e.target.value });
    }


/*
    render(){
        return ce('div', null, 'Message App V2.2',
                    ce('br'),
                    ce('ul', null,
                        //fetch: 
                        ce('li', null, this.state.messageList.map(message => ce('li', null, message)))
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
*/

    render() {
        const messageItems = this.state.messageList.map(message => this.renderMessageItem(message));
        
        return  ce('div', 
                    null,
                    ce('h2', null, 'Message App V2.2'),
                    ce('br'),
                    ce('span', { id: 'put-mess' }),
                    ce('br'),
                    ce('ul', { id: 'message-list' }, ...messageItems),
                    ce('input', {
                        type: 'text',
                        id: 'message-to',
                        value: this.state.to,
                        onChange: e => this.handleToInputChange(e)
                    }),
                    ce('br'),
                    ce('textarea', {
                        id: 'message-content',
                        value: this.state.content,
                        onChange: e => this.handleContentInputChange(e)
                    }),
                    ce('button', { onClick: e => this.handlePutClick(e) }, 'Put Message'),
                    ce('br'),
                    ce('button', {onClick: e => this.props.doLogout()}, 'Log Out')
                );
    }

    handlePutClick(e){
        const from = this.state.messageTo;
        const content = this.state.messageContent;
        const to = this.state.messageTo;
        console.log("handlePutClick() is called.")
        fetch(putRoute, { 
            method: 'POST',
            headers: {'Content-Type': 'application/json', 'Csrf-Token':csrfToken},
            body: JSON.stringify({from, content, to})
        }).then(res => res.json()).then(data => {
            console.log(data);
            if (data){
                this.loadMessages();    
                this.setState({putMess:""})
                //document.getElementById("message-to").value = "";
                //document.getElementById("message-content").value = "";
                //document.getElementById("put-mess").innerHTML = "";
            }
            else{
                this.setState({putMess:"Put Message failed."})
                //document.getElementById("put-mess").innerHTML = "Put Message Failed";
            }
        });
    }


    loadMessages() {
        fetch(messagesRoute).then(res => res.json()).then(messages => this.setState({messageList:messages}))
                                                    .catch(error => console.error('Error fetching messages:', error));
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