class Auth {
    constructor() {
      this.authenticated = false;
      this.user = undefined;
    }
  
    login(cb, user) {
      this.authenticated = true;
      this.user = user;
      cb();
    }
  
    logout(cb) {
      this.authenticated = false;
      this.user = undefined
      cb();
    }
  
    isAuthenticated() {
      return this.authenticated;
    }
  }
  
  export default new Auth();