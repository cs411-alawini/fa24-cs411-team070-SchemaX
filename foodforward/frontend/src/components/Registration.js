// src/components/Registration.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Registration() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [phone, setPhone] = useState('');
    const [location, setLocation] = useState('');
    const [type, setType] = useState('Recipient');
    const [notificationEnabled, setNotificationEnabled] = useState(false);

    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();

        const payload = {
            name,
            email,
            password,
            phone,
            location,
            type,
        };

        if (type === 'Recipient') {
            payload.notificationEnabled = notificationEnabled;
        }

        try {
            const response = await fetch('http://localhost:8080/users/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(payload),
            });

            if (response.ok) {
                alert('Registration successful! You can now log in.');
                navigate('/login');
            } else {
                alert('Registration failed. Please try again.');
            }
        } catch (error) {
            console.error('Error registering user:', error);
            alert('An error occurred. Please try again.');
        }
    };

    return (
        <div style={styles.container}>
            <div style={styles.formContainer}>
                <h2 style={styles.header}>User Registration</h2>
                <form style={styles.form} onSubmit={handleRegister}>
                    <label style={styles.label} htmlFor="name">Full Name</label>
                    <input
                        style={styles.input}
                        type="text"
                        id="name"
                        name="name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        placeholder="Enter your full name"
                        required
                    />

                    <label style={styles.label} htmlFor="email">Email Address</label>
                    <input
                        style={styles.input}
                        type="email"
                        id="email"
                        name="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="Enter your email"
                        required
                    />

                    <label style={styles.label} htmlFor="password">Password</label>
                    <input
                        style={styles.input}
                        type="password"
                        id="password"
                        name="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder="Enter your password"
                        required
                    />

                    {/* Phone */}
                    <label style={styles.label} htmlFor="phone">Phone Number</label>
                    <input
                        style={styles.input}
                        type="text"
                        id="phone"
                        name="phone"
                        value={phone}
                        onChange={(e) => setPhone(e.target.value)}
                        placeholder="Enter your phone number"
                        required
                    />

                    <label style={styles.label} htmlFor="location">Location</label>
                    <input
                        style={styles.input}
                        type="text"
                        id="location"
                        name="location"
                        value={location}
                        onChange={(e) => setLocation(e.target.value)}
                        placeholder="Enter your location"
                    />

                    <label style={styles.label}>User Type</label>
                    <div style={styles.radioGroup}>
                        <label>
                            <input
                                type="radio"
                                name="type"
                                value="Recipient"
                                checked={type === 'Recipient'}
                                onChange={(e) => setType(e.target.value)}
                            />
                            Recipient
                        </label>
                        <label>
                            <input
                                type="radio"
                                name="type"
                                value="Donor"
                                checked={type === 'Donor'}
                                onChange={(e) => setType(e.target.value)}
                            />
                            Donor
                        </label>
                    </div>

                    {type === 'Recipient' && (
                        <>
                            <div style={{ marginBottom: '15px' }}>
                                <input
                                    type="checkbox"
                                    id="notificationEnabled"
                                    name="notificationEnabled"
                                    checked={notificationEnabled}
                                    onChange={(e) => setNotificationEnabled(e.target.checked)}
                                />
                                <label htmlFor="notificationEnabled" style={{ marginLeft: '8px' }}>
                                    Enable Notifications
                                </label>
                            </div>
                        </>
                    )}

                    <button style={styles.button} type="submit">Register</button>
                </form>

                <button
                    style={{ ...styles.button, backgroundColor: '#007bff', marginTop: '10px' }}
                    onClick={() => navigate('/login')}
                >
                    Already Registered? Login Here
                </button>
            </div>
        </div>
    );
}

const styles = {
    container: {
        fontFamily: 'Arial, sans-serif',
        backgroundColor: '#f4f4f4',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        height: '100vh',
        margin: 0,
    },
    formContainer: {
        backgroundColor: '#fff',
        padding: '20px',
        borderRadius: '8px',
        boxShadow: '0 2px 10px rgba(0, 0, 0, 0.1)',
        width: '360px',
        textAlign: 'center',
    },
    header: {
        color: '#333',
        marginBottom: '20px',
    },
    form: {
        display: 'flex',
        flexDirection: 'column',
    },
    label: {
        marginBottom: '6px',
        fontWeight: 'bold',
    },
    input: {
        padding: '10px',
        marginBottom: '15px',
        borderRadius: '4px',
        border: '1px solid #ccc',
        fontSize: '16px',
    },
    radioGroup: {
        display: 'flex',
        justifyContent: 'space-around',
        marginBottom: '15px',
    },
    button: {
        padding: '10px',
        backgroundColor: '#28a745',
        color: '#fff',
        borderRadius: '4px',
        border: 'none',
        fontSize: '16px',
        cursor: 'pointer',
    },
};

export default Registration;