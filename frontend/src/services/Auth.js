class Auth {
    constructor() {
        this.user = JSON.parse(sessionStorage.getItem("user"))
        console.log(this.user)
        this.authenticated = this.user !== null ? true : false;
    }
  
    login(cb, user) {
      this.authenticated = true;
      this.user = user;
      sessionStorage.setItem("user", JSON.stringify(this.user));
      cb();
    }
  
    logout(cb) {
      this.authenticated = false;
      this.user = undefined;
      sessionStorage.setItem("user", undefined);
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