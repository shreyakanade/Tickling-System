import {useState} from 'react';
import Router from 'next/router';
import {api} from '../lib/api';
export default function Register(){
  const [username,setUsername]=useState(''); const [password,setPassword]=useState(''); const [msg,setMsg]=useState('');
  const submit=async(e)=>{e.preventDefault();
    const r = await api('/auth/register', null, {method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify({username,password})});
    if(r.ok){ Router.push('/'); } else setMsg(JSON.stringify(r));
  };
  return (<div><h2>Register</h2><form onSubmit={submit}><input placeholder='username' value={username} onChange={e=>setUsername(e.target.value)} /><input placeholder='password' type='password' value={password} onChange={e=>setPassword(e.target.value)} /><button>Register</button></form><p>{msg}</p></div>);
}
