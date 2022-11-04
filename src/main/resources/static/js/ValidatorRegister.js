function blockSubmit(submit){
    submit.disabled = true
    submit.style.backgroundColor = '#f00'
}
function activeSubmit(submit){
    submit.disabled = false
    submit.style.backgroundColor = '#0085FC'
}
function checkFormValid(){
    let inputElements = document.querySelectorAll("input")
    let valid = ((value) => {
        return value.trim().length < 3 ? false : (value.trim().length > 256 ? false : true)
    })(inputElements[0].value)// check nickname valid
    valid *= ((value) => {
         return value.trim().length < 3 ? false : (value.trim().length > 256 ? false : true)
    })(inputElements[1].value) // check username valid
    valid *= ((value) => {
         return value.trim().length < 6 ? false : (value.trim().length > 256 ? false : true)
    })(inputElements[2].value) // check password valid
    valid *= ((value,rePassword) => {
         return value == rePassword;
    })(inputElements[2].value,inputElements[3].value) // check re Password

    if (valid){
        activeSubmit(document.querySelector("button"))
    }else{
        blockSubmit(document.querySelector("button"))
    }
}
function Validator(options) {
    var formElement = document.querySelector(options.form)
    options.rules.forEach(function (rule) {
        var inputElement = formElement.querySelector(rule.params) /// Lấy ra the input
        if (inputElement) {// blur kiem tra xem nguoi dung vua bo con tro chuot ra ngoai
            inputElement.onblur = function () {
                var errMSG = rule.test(inputElement.value);
                var errEle = inputElement.parentElement.parentElement.querySelector('msg')
                if (errMSG) {
                    errEle.innerText = errMSG
                    inputElement.classList.add('invalid')
                    inputElement.classList.remove('valid')
                } else {
                    errEle.innerText = ''
                    inputElement.classList.add('valid')
                    inputElement.classList.remove('invalid')
                }
            }
        }
    });
}

Validator.checkName = function (params) {
    return {
        params: params,
        test: function (value) {
            return value.trim().length < 3 ? "Too short! Use 3 to 256 characters for name" : (value.trim().length > 256 ? "Too long! Use 3 to 256 characters for name" : undefined)
        }
    }
}
Validator.checkUsername = function (params) {
    return {
        params: params,
        test: function (value) {
            return value.trim().length < 3 ? "Too short! Use 3 to 256 characters for username" : (value.trim().length > 256 ? "Too long! Use 3 to 256 characters for username" : undefined)
        }
    }
}
Validator.checkPassword = function (params) {
    return {
        params: params,
        test: function (value) {
            pass = value.trim();
            return value.trim().length < 6 ? "Too short! Use 6 to 256 characters for password" : (value.trim().length > 256 ? "Too long! Use 3 to 256 characters for password" : undefined)
        }
    }
}
Validator.checkRePass = function (params) {
    return {
        params: params,
        test: function (value) {
            return value.trim() == pass ? (value.trim().length < 6 ? "Re-password not match" : undefined) : 'Those passwords didn’t match. Try again.'
        }
    }
}