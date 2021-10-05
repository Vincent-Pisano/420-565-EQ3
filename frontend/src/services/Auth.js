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
      this.user = undefined;
      cb();
    }
  
    isAuthenticated() {
      return this.authenticated;
    }

    isStudent() {
      return this.authenticated ? this.user.username.startsWith("E") : false;
    }

    isSupervisor() {
      return this.authenticated ? this.user.username.startsWith("S") : false;
    }

    isMonitor() {
      return this.authenticated ? this.user.username.startsWith("M") : false;
    }

    isInternshipManager() {
      return this.authenticated ? this.user.username.startsWith("G") : false;
    }
  }
  
  export default new Auth();