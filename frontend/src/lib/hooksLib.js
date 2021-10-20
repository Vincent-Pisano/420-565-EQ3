import { useState } from "react";

export function useFormFields(initialState) {
  const [fields, setValues] = useState(initialState);

  const workDaysArray = [
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Sunday",
    "Saturday",
  ];

  function handleWorkDay(event) {
    let newWorkDays = fields.workDays;
    if (newWorkDays.some((workday) => event.currentTarget.id === workday))
      newWorkDays = newWorkDays.filter(
        (workday) => event.currentTarget.id !== workday
      );
    else newWorkDays.push(event.currentTarget.id);
    return newWorkDays;
  }

  return [
    fields,
    function (event) {
      if (workDaysArray.some((workday) => event.currentTarget.id === workday)) {
        setValues({
          ...fields,
          workDays: handleWorkDay(event),
        });
      } else {
        setValues({
          ...fields,
          [event.currentTarget.id]: event.target.value,
        });
      }
    },
  ];
}
