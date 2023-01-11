let objectToInspect = window,
    result = [];
while(objectToInspect !== null) {
    result = result.concat(Object.getOwnPropertyNames(objectToInspect));
    objectToInspect = Object.getPrototypeOf(objectToInspect);
}

result.forEach(prop => {
    if (prop.match(/.+_.+_(Array|Promise|Symbol)/ig)) {
        delete window[prop]
        console.log('removed', prop)
    }
});
