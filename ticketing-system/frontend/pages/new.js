import {useState} from 'react';
import Router from 'next/router';
import {api} from '../lib/api';
export default function New(){
  const [subject,setSubject]=useState(''); const [description,setDescription]=useState(''); const [priority,setPriority]=useState('MEDIUM');
  const submit=async(e)=>{e.preventDefault(); const token=localStorage.getItem('token'); const r = await api('/tickets/create', token, {method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify({subject,description,priority})}); Router.push('/dashboard'); };
  return (<div><h2>Create Ticket</h2><form onSubmit={submit}><input placeholder='subject' value={subject} onChange={e=>setSubject(e.target.value)} /><textarea placeholder='description' value={description} onChange={e=>setDescription(e.target.value)}></textarea><select value={priority} onChange={e=>setPriority(e.target.value)}><option>LOW</option><option>MEDIUM</option><option>HIGH</option></select><button>Create</button></form></div>);
}
