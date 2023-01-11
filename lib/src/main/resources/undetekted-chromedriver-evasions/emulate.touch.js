Object.defineProperty(navigator, 'maxTouchPoints', {
    get: () => 1
}); 
Object.defineProperty(navigator.connection, 'rtt', {
    get: () => 100
});
