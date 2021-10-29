class Auth {
  constructor() {
    if (sessionStorage.getItem("user") !== null) {
      this.user = JSON.parse(sessionStorage.getItem("user"));
      this.authenticated = true;
    } else {
      this.user = undefined;
      this.authenticated = false;
    }
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
    sessionStorage.removeItem("user");
    cb();
  }

  updateUser(user) {
    this.user = user;
    sessionStorage.setItem("user", JSON.stringify(this.user));
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
