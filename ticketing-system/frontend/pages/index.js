import {useState} from 'react';
import Router from 'next/router';
import {api} from '../lib/api';

export default function Login(){
  const [username,setUsername]=useState('');
  const [password,setPassword]=useState('');
  const [msg,setMsg]=useState('');
  const submit=async(e)=>{e.preventDefault();
    const r = await api('/auth/login', null, {method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify({username,password})});
    if(r.token){ localStorage.setItem('token', r.token); localStorage.setItem('username', r.username); Router.push('/dashboard'); }
    else setMsg(JSON.stringify(r));
  };
  return (<div>
    <h2>Login</h2>
    <form onSubmit={submit}>
      <input placeholder='username' value={username} onChange={e=>setUsername(e.target.value)} />
      <input placeholder='password' type='password' value={password} onChange={e=>setPassword(e.target.value)} />
      <button>Login</button>
    </form>
    <p>{msg}</p>
    <p>Or <a href="/register">Register</a></p>
  </div>);
}
