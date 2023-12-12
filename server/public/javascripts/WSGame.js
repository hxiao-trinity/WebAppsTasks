document.addEventListener('DOMContentLoaded', function() {
    const canvas = document.getElementById('game-canvas');
    const ctx = canvas.getContext('2d');
    const socket = new WebSocket(`ws://${window.location.host}/gameSocket`);

    let playerId = 'player_' + Math.random().toString(36).substr(2, 9);
    let playerX = canvas.width/2; 
    let playerY = canvas.height/2; 


    socket.onmessage = function(event) {
        const gameState = JSON.parse(event.data);
        renderGame(gameState);
    };

    function renderGame(gameState) {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.beginPath();
        ctx.rect(0, 0, canvas.width, canvas.height);
        ctx.stroke();
        for (const [id, player] of Object.entries(gameState.players)) {
            ctx.fillRect(player.x, player.y, 10, 10);
        }
    }

    document.addEventListener('keydown', function(event) {
        switch (event.key) {
            case "ArrowUp": playerY = Math.max(playerY-5, 0); break;
            case "ArrowDown": playerY = Math.min(playerY+5, canvas.height-10); break;
            case "ArrowLeft": playerX = Math.max(playerX-5, 0); break;
            case "ArrowRight": playerX = Math.min(playerX+5, canvas.width-10); break;
        }

        // Send the updated position to the server
        socket.send(JSON.stringify({ id: playerId, x: playerX, y: playerY }));
    });
/*
    document.addEventListener('keydown', function(event) {
        let dx = 0, dy = 0;
        if (event.key === "ArrowUp") dy = -5;
        else if (event.key === "ArrowDown") dy = 5;
        else if (event.key === "ArrowLeft") dx = -5;
        else if (event.key === "ArrowRight") dx = 5;

        if (dx !== 0 || dy !== 0) {
            socket.send(JSON.stringify({ id: playerId, x: dx, y: dy }));
        }
    });
*/


});
