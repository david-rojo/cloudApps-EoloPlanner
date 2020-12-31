function GetWeather(call, callback){

    function startsByVowel(s) {
        return ['a', 'e', 'i', 'o', 'u'].indexOf(s[0].toLowerCase()) !== -1
    }

    function randomProcessTime() {  
        return Math.floor(
          Math.random() * (3 - 1) + 1
        )
      }

    console.log('Request received: ' + JSON.stringify(call));

    var { city } = call.request;

    let weather = startsByVowel(city) ? "Rainy" : "Sunny";
    let processTime = randomProcessTime();

    console.log('Random process time for request for city ' + city + ' is ' +  processTime + ' second(s)');

    setTimeout(function () {
        callback(null, { 
            city: city,
            weather: weather })
    }, randomProcessTime);
}

exports.GetWeather = GetWeather;