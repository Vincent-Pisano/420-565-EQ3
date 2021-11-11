const SessionDropdown = ({ sessions, currentSession, changeCurrentSession }) => {
    return (
        <div className="menu-item">
          <p className="menu-item-title">{currentSession}</p>
          <ul>
            {sessions.map((session, i) => (
              <li key={i}>
                <button
                  className={
                    "menu-item-button" +
                    (currentSession === session
                      ? " menu-item-button-selected"
                      : "")
                  }
                  onClick={() => changeCurrentSession(session)}
                >
                  {session}
                </button>
              </li>
            ))}
          </ul>
        </div>
      );
};

export default SessionDropdown;
