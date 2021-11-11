let session = "";
let date = new Date();
if (date.getUTCMonth() > 2 && date.getUTCMonth() < 8) {
  session = date.getFullYear() + " Été";
} else {
  session = date.getFullYear() + 1 + " Hiver";
}

export { session }