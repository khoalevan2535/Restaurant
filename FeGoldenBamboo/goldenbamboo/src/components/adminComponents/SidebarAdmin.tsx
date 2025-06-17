import React from 'react'
import { Link } from 'react-router-dom'
export default function sidebarAdmin() {
  return (
    <div>
      <nav className="sidebar">
        <ul className="list-group list-group-flush">
          <li className="sidebar-item my-2">
            <Link to="/Admin/Dashboard" className="link-body-emphasis ">Dashboard</Link>
          </li> 
          <div className='mb-2'>
          <button type="button" className="btn btn-primary w-100" data-bs-toggle="collapse" data-bs-target="#branch">Chi nhánh</button>
          <div id="branch" className="collapse">
            <ul>
              <li><Link to="/Admin/Branch/List">List</Link></li>
              <li><Link to="/Admin/Branch/Create">Create</Link></li>
              <li><Link to="/Admin/Branch/Update/:branchId">Update</Link></li>
            </ul>
          </div>
          </div>
  
          <li className="sidebar-item my-2">
            <Link to="/Admin/Branch" className="link-body-emphasis ">Chi nhánh</Link>
          </li>
          <li className="sidebar-item my-2">
            <Link to="/Admin/users" className="link-body-emphasis ">Users</Link>
          </li>
          <li className="sidebar-item my-2">
            <Link to="/Admin/settings" className="link-body-emphasis ">Settings</Link>
          </li>
          <li className="sidebar-item my-2">
            <Link to="/Admin/reports" className="link-body-emphasis ">Reports</Link>
          </li>
          <li className="sidebar-item my-2">
            <Link to="/Admin/logs" className="link-body-emphasis ">Logs</Link>
          </li>
          <li className="sidebar-item my-2">
            <Link to="/Admin/notifications" className="link-body-emphasis ">Notifications</Link>
          </li>
          <li className="sidebar-item my-2">
            <Link to="/Admin/help" className="link-body-emphasis ">Help</Link>
          </li>
          <li className="sidebar-item my-2">
            <Link to="/admin/logout" className="link-body-emphasis ">Logout</Link>
          </li>
        </ul>
      </nav>

      
   </div>
  )
}
