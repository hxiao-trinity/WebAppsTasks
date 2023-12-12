
    const canvas = document.getElementById('game-canvas');
    const ctx = canvas.getContext('2d');
    //const socket = new WebSocket('ws://localhost:9000/WSGame');
    
    const socketRoute = document.getElementById("ws-route").value;
    const socket = new WebSocket(socketRoute.replace("http", "ws"));

    let player = { x: 50, y: 50, id: 1 }; 

  
    socket.onmessage = function(event) {
        const players = JSON.parse(event.data);
        drawPlayers(players);
    };

  
    document.addEventListener('keydown', function(event) {
        const clamp = (min, x, max) => {
            return (x < min) ? min : (x > max) ? max : x;
        };
        const WIDTH = 800-25;
        const HEIGHT = 600-25;
        switch(event.key) {
            case 'ArrowUp': player.y -= 25; player.y = clamp(0, player.y, HEIGHT); break;
            case 'ArrowDown': player.y += 25; player.y = clamp(0, player.y, HEIGHT); break;
            case 'ArrowLeft': player.x -= 25; player.x = clamp(0, player.x, WIDTH); break;
            case 'ArrowRight': player.x += 25; player.x = clamp(0, player.x, WIDTH); break;
            
        }
        socket.send(JSON.stringify(player));
        drawPlayer(player);
    });

    // Draw Player Function
    function drawPlayer(player) {
        ctx.clearRect(0, 0, canvas.width, canvas.height); 
        ctx.fillStyle = 'blue'; 
        ctx.fillRect(player.x, player.y, 10, 10); 
    }

    // Draw All Players
    function drawPlayers(players) {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        players.forEach(p => {
            ctx.fillStyle = (p.id === player.id) ? 'blue' : 'red'; 
            ctx.fillRect(p.x, p.y, 10, 10);
        });
    }

    // Initial Draw
    drawPlayer(player);
