const Student = ({student}) => {
    return (
        <li className="list_node">{student.firstName} {student.lastName}</li>
    )
}

export default Student
