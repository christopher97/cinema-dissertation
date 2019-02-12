export default {
    reset (model) {
        for (let key in model) {
            if (typeof model[key] === 'string') model[key] = ''
            else model[key] = null
        }

        return model
    },
    map (model, data) {
        if (data.length <= 0) return

        let responses = []

        for (let i = 0; i < data.length; i++) {
            let raw = data[i]
            let response = {}

            for (let key in model) {
                response[key] = raw[key]
            }
            response['onedit'] = false
            responses.push(response)
        }

        return responses
    },
    single (model, data) {
        let response = {}

        for (let key in model) {
            response[key] = data[key]
        }
        response['onedit'] = false

        return response
    },
    dateToString (date) {
        let temp = new Date(date)
        let zero = '0'

        let yyyy = temp.getFullYear().toString()
        let mm = (temp.getMonth() + 1).toString()
        let dd = temp.getDate().toString()

        let mmChars = mm.split('')
        let ddChars = dd.split('')

        return yyyy + '-' + (mmChars[1] ? mm : zero + mmChars[0]) + '-' + (ddChars[1] ? dd : zero + ddChars[0])
    }
}
