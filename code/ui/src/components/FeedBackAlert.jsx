import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheckCircle, faExclamationCircle, faExclamationTriangle, faInfoCircle } from '@fortawesome/free-solid-svg-icons';

const FeedBackAlert = ({ message, type }) => {
    const generateIcon = () => {
        if (type === 'success') {
            return <FontAwesomeIcon icon={faCheckCircle} size='xl'/>;
        }
        if (type === 'error') {
            return <FontAwesomeIcon icon={faExclamationCircle} size='xl'/>;
        }
        if (type === 'warning') {
            return <FontAwesomeIcon icon={faExclamationTriangle} size='xl'/>;
        }
        return <FontAwesomeIcon icon={faInfoCircle} size='xl'/>;
    }

    return (
        <div className={`feedback-alert feedback-alert-${type}`}>
            <span>{message}</span>
            <span>{generateIcon()}</span>
        </div>
    );
}

export default FeedBackAlert;