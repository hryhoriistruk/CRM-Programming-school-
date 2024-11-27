const baseURL = 'http://localhost:3000'
const orders='/orders'
const auth='/auth'
const urls = {
   orders:{
       base:orders,
   },
   auth:{
       signUpAdmin:`${auth}/admin/sign-up`,
       signUp:`${auth}/sign-up`,
       signIn:`${auth}/sign-in`,
       refresh:`${auth}/refresh`
   }

}

export {
    baseURL,
    urls
}