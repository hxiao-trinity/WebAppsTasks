
    const canvas = document.getElementById('gameCanvas');
    const ctx = canvas.getContext('2d');
    const socket = new WebSocket('ws://localhost:9000/WSGame'); // Replace with your server address

    let player = { x: 50, y: 50, id: 'unique_player_id' }; // Add a unique identifier for each player

    // Handle WebSocket Messages
    socket.onmessage = function(event) {
        const players = JSON.parse(event.data);
        drawPlayers(players);
    };

    // Event Listener for Arrow Keys
    document.addEventListener('keydown', function(event) {
        switch(event.key) {
            case 'ArrowUp': player.y -= 5; break;
            case 'ArrowDown': player.y += 5; break;
            case 'ArrowLeft': player.x -= 5; break;
            case 'ArrowRight': player.x += 5; break;
        }
        socket.send(JSON.stringify(player));
        drawPlayer(player);
    });

    // Draw Player Function
    function drawPlayer(player) {
        ctx.clearRect(0, 0, canvas.width, canvas.height); // Clear the canvas
        ctx.fillStyle = 'blue'; // Set player color
        ctx.fillRect(player.x, player.y, 10, 10); // Draw the player
    }

    // Draw All Players
    function drawPlayers(players) {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        players.forEach(p => {
            ctx.fillStyle = p.id === player.id ? 'blue' : 'red'; // Differentiate the current player
            ctx.fillRect(p.x, p.y, 10, 10);
        });
    }

    // Initial Draw
    drawPlayer(player);
