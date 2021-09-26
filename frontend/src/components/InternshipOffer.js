const Student = ({internshipOffer, onToggle}) => {
    return (
        <li className="list_node"
        onDoubleClick={() => onToggle(internshipOffer)}>
            {internshipOffer.jobName}, {internshipOffer.city}
        </li>
    )
}

export default Student