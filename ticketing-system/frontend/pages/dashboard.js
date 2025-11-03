import {useEffect,useState} from 'react';
import Link from 'next/link';
import Router from 'next/router';
import {api} from '../lib/api';
export default function Dashboard(){
  const [tickets,setTickets]=useState([]);
  useEffect(()=>{ const t = localStorage.getItem('token'); if(!t) Router.push('/'); fetchTickets(t); },[]);
  async function fetchTickets(t){ const r = await api('/tickets/my', localStorage.getItem('token'), {method:'GET'}); setTickets(r || []); }
  return (<div><h2>My Tickets</h2><p><Link href='/new'>Create Ticket</Link> | <a onClick={()=>{localStorage.clear(); Router.push('/');}}>Logout</a></p>{tickets.map(ticket=> (<div className='ticket' key={ticket.id}><h3>{ticket.subject} (#{ticket.id})</h3><p>{ticket.description}</p><p>Status: {ticket.status} Priority: {ticket.priority}</p><p><Link href={'/ticket/'+ticket.id}>View</Link></p></div>))}</div>);
}
